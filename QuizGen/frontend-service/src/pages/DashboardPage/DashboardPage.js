import {
    BarElement,
    CategoryScale,
    Chart as ChartJS,
    Legend,
    LinearScale,
    LineElement,
    PointElement, 
    Title,
    Tooltip,
    ArcElement
} from 'chart.js';
import React from 'react';
import { Bar, Pie} from 'react-chartjs-2';
import './DashboardPage.css';


// Register the components
ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    LineElement, 
    PointElement, 
    Title,
    Tooltip,
    Legend,
    ArcElement
);

const DashboardPage = () => {
    const levelBasedAvgScores = {
        labels: ['Easy', 'Medium', 'Difficult'],
        datasets: [
            {
                label: 'Scores In Percentages',
                data: [90, 85, 80],
                backgroundColor: ['rgba(75, 192, 192, 0.35)', 'rgba(153, 102, 255, 0.35)', 'rgba(255, 99, 132, 0.35)'],
                borderColor: ['rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 99, 132, 1)'],
                borderWidth: 1.5,
            },
        ],
    };

    const topTenUserQuizData = {
        labels:['Quiz-1', 'Quiz-2', 'Quiz-3','Quiz-4', 'Quiz-5', 'Quiz-6','Quiz-7', 'Quiz-8', 'Quiz-9'],
        datasets: [
            {
                label: 'Scores out of 20 questions',
                data:  [20, 15, 12,20, 10, 11,20, 17, 7,19],
                backgroundColor: 'rgba(54, 162, 235, 0.35)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1,
            },
        ],
    };

    const topTenUserQuizDataOptions = {
        responsive: true, 
        indexAxis: 'x',
        maintainAspectRatio: true, 
        scales: {
            y: {
              beginAtZero: true,
            },
            
          },
          datasets: {
            bar: {
              barPercentage: 0.75,  // Decrease this value to increase space between bars
              categoryPercentage: 0.8,  // Decrease this value to increase space between categories
            },
          },
    }

    
    const userAvgScore = {
        labels:['Avg Score'],
        datasets: [
            {
                label: 'User average Score of all quiz',
                data:  [90,10],
                backgroundColor: ['rgba(235, 126, 224, 0.35)' ,'rgba(255, 255, 255, 0.35)'],
                borderColor: ['rgba(235, 126, 224, 1)', 'rgba(255, 255, 255, 1)'],
                borderWidth: 1.5,
            },
        ],
    };


    return (
        <div className="dashboard-page" style={{
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            }}> 

            <div className="dashboard-title-container">
                <div className = "dashboard-title-txt">
                    <h1> User Quiz Tracking Dashboard</h1>
                </div>
            </div>     

            <div className="stats-row">
                <div className="stats-card">
                    <h2>Level Based Avg Score</h2>
                    <Bar data={levelBasedAvgScores} />
                </div>

                <div className="stats-card">
                    <h2> Latest 10 Quiz Tracking Stats</h2>
                    <Bar data={topTenUserQuizData} options={topTenUserQuizDataOptions} />
                </div> 

                <div className="stats-card">
                    <h2> User Average Score</h2>
                    <Pie data={userAvgScore} options={{ responsive: true }} width={700} height={700} />
                </div>                
            </div>            
        </div>
    );
};

export default DashboardPage;
